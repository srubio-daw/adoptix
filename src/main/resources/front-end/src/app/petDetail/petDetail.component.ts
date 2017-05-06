import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';

// MODULOS
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';

// INTERNAL
import { ValidationService } from '../services/validation.service';
import { UserService } from '../services/user.service';
import { ProvinceService } from '../services/province.service';
import { PetService } from '../services/pet.service';
import { RequestService } from '../services/request.service';
import { ErrorModalComponent } from '../modal/error-modal.component';

@Component({
  templateUrl: 'petDetail.html',
  providers: [ValidationService, ProvinceService, PetService, RequestService]
})
export class PetDetailComponent {
	constructor(fb: FormBuilder, private validationService : ValidationService, private userService : UserService,
		private provinceService : ProvinceService, private petService : PetService, 
    private activatedRoute: ActivatedRoute, private requestService : RequestService) {
      this.requestForm = fb.group({
        userMail: [null],
        formPet: [null],
        kidsAtHome: [0, Validators.compose([Validators.required, validationService.integer])],
        catsAtHome: [0, Validators.compose([Validators.required, validationService.integer])],
        dogsAtHome: [0, Validators.compose([Validators.required, validationService.integer])],
        comment: [null],
        adoptOrHost: [null],
        phone: [null, Validators.compose([Validators.required, validationService.phone])]
      });
      this.userMail = this.requestForm.controls['userMail'];
      this.formPet = this.requestForm.controls['formPet'];
      this.kidsAtHome = this.requestForm.controls['kidsAtHome'];
      this.dogsAtHome = this.requestForm.controls['dogsAtHome'];
      this.catsAtHome = this.requestForm.controls['catsAtHome'];
      this.comment = this.requestForm.controls['comment'];
      this.adoptOrHost = this.requestForm.controls['adoptOrHost'];
      this.phone = this.requestForm.controls['phone'];
    }

    // Inicialización
    ngOnInit() { 
        this.activatedRoute.params.subscribe((params: Params) => {
            this.petId = params['petId'];
            this.getPet();
            this.getPetVetVisits(false);
            this.getPetVaccines(false);
            this.getPetMedicalTests(false);
        });
    }

    // Formulario de solicitud
    requestForm : FormGroup;
    userMail : AbstractControl;
    formPet : AbstractControl;
    kidsAtHome : AbstractControl;
    dogsAtHome : AbstractControl;
    catsAtHome : AbstractControl;
    comment : AbstractControl;
    phone : AbstractControl;
    adoptOrHost : AbstractControl;

    allowedToAdopt : boolean = false;
    allowedToHost : boolean = false;

    @ViewChild(ErrorModalComponent)
	  errorModal : ErrorModalComponent;

    @ViewChild('requestModal')
    requestModal : ModalComponent;

    petId : number = null;
    pet : any = {};
    petVaccines : any = [];
    vaccinesPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };
    petMedicalTests : any = [];
    medicalTestsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };
    petVetVisits : any = [];
    vetVisitsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };

    adopt() {
      this.adoptOrHost.setValue(true);
      this.userMail.setValue(this.userService.loggedUser.name);
      this.requestModal.open();
    }

    host() {
      this.adoptOrHost.setValue(false);
      this.userMail.setValue(this.userService.loggedUser.name);
      this.requestModal.open();
    }

    getPreviousAdoptionRequests() {
      let result = this.requestService.getByUserAndPet(this.userService.loggedUser.name, this.pet.id, true).subscribe(
        result => {
          if (!result.success) {
            this.errorModal.open('error.title', result.message);
          } else {
            if (result.totalRecords == 0) {
              this.allowedToAdopt = true;
            }
          }
        }, 
        error => {
          console.log(JSON.stringify(error.json()))
          return null;
        }
      );
    }

    getPreviousHostRequests() {
      let result = this.requestService.getByUserAndPet(this.userService.loggedUser.name, this.pet.id, false).subscribe(
        result => {
          if (!result.success) {
            this.errorModal.open('error.title', result.message);
          } else {
            if (result.totalRecords == 0) {
              this.allowedToHost = true;
            }
          }
        }, 
        error => {
          console.log(JSON.stringify(error.json()))
          return null;
        }
      );
    }

    resetRequestForm() {
      this.requestForm.reset();
      this.userMail.setValue(this.userService.loggedUser.name);
      this.formPet.setValue(this.pet.id);
      this.kidsAtHome.setValue(0);
      this.catsAtHome.setValue(0);
      this.dogsAtHome.setValue(0);
      this.requestForm.updateValueAndValidity();
    }

    submitRequestForm() {
      if (!this.requestForm.valid) {
        this.validationService.markFormAsTouched(this.requestForm);
      } else {
        let request = this.requestForm.value;
        request['pet'] = request['formPet'];
        let result = this.requestService.save(request).subscribe(
          result => {
            if (!result.success) {
              let title = null;
              this.errorModal.open('error.title', result.message);
            } else {
              let title = null;
              if (this.adoptOrHost.value) {
                title = 'pet.requestAdoption';
                this.allowedToAdopt = false;
              } else {
                title = 'pet.requestHost';
                this.allowedToHost = false;
              }
              this.resetRequestForm();
              this.requestModal.close();
              this.errorModal.open(title, 'request.requestSent');
            }
          }, 
          error => {
            console.log(JSON.stringify(error.json()))
            return null;
          }
        );
      }
    }

    getAdopterName(name : string) {
      if (name == null) {
        return "pet.notAdopted";
      }
      return name;
    }

    getHostName(name : string) {
      if (name == null) {
        return "pet.notHosted";
      }
      return name;
    }

    getPet() {
        this.petService.getPet(this.petId)
            .subscribe(
               result => {
                   this.pet = result.data;
                   this.formPet.setValue(this.pet.id);
                   if (this.userService.hasRole('usuario')) {
                     this.getPreviousAdoptionRequests();
                     this.getPreviousHostRequests();
                   }
               },
               error =>  alert(error));
    }

    getPetTypeLabel(petType : number) {
        switch (petType) {
          case 1:
            return "pet.dog";
          case 2:
            return "pet.cat";
          default:
            return "Pet type undefined";
        }
    }

    getGenderLabel(gender : string) {
      switch (gender) {
        case "f":
          return "pet.female";
        case "m":
          return "pet.male";
        default:
          return "Pet gender undefined";
      }
    }

    getCheckboxIcon(status : boolean) {
        if (status) {
          return "glyphicon glyphicon-check";
        } else {
          return "glyphicon glyphicon-unchecked";
        }
    }

    getPetVaccines(rowsChange : boolean) {
        if (rowsChange) {
            this.vaccinesPagination.page = 1;
        }
        this.petService.getPetVaccines(this.petId, this.vaccinesPagination)
            .subscribe(
               result => {
                    this.petVaccines = result.data;
                    this.vaccinesPagination.totalRecords = result.totalRecords;
                    this.vaccinesPagination.pages = this.getPages(this.vaccinesPagination);
               },
               error =>  alert(error));
    }

    getPetVetVisits(rowsChange : boolean) {
        if (rowsChange) {
            this.vetVisitsPagination.page = 1;
        }
        this.petService.getPetVetVisits(this.petId, this.vetVisitsPagination)
            .subscribe(
               result => {
                   this.petVetVisits = result.data;
                   this.vetVisitsPagination.totalRecords = result.totalRecords;
                   this.vetVisitsPagination.pages = this.getPages(this.vetVisitsPagination);
               },
               error =>  alert(error));
    }

    getPetMedicalTests(rowsChange : boolean) {
        if (rowsChange) {
            this.medicalTestsPagination.page = 1;
        }
        this.petService.getPetMedicalTests(this.petId, this.medicalTestsPagination)
            .subscribe(
               result => {
                   this.petMedicalTests = result.data;
                   this.medicalTestsPagination.totalRecords = result.totalRecords;
                   this.medicalTestsPagination.pages = this.getPages(this.medicalTestsPagination);
               },
               error =>  alert(error));
    }

    getPages(pagination : any) {
        if (pagination.totalRecords == 0) {
          return [];
        } else {
          let records = pagination.totalRecords;
          let pages = [];
          for (let i = 0; records > 0; i++) {
            pages[i] = i + 1;
            records = records - pagination.rows;
          }
          return pages;
        }
    }


}