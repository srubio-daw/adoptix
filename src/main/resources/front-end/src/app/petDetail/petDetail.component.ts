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
import { ErrorModalComponent } from '../modal/error-modal.component';

@Component({
  templateUrl: 'petDetail.html',
  providers: [ValidationService, ProvinceService, PetService]
})
export class PetDetailComponent {
	constructor(fb: FormBuilder, private validationService : ValidationService, private userService : UserService,
		private provinceService : ProvinceService, private petService : PetService, private activatedRoute: ActivatedRoute) {

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

    @ViewChild(ErrorModalComponent)
	errorModal : ErrorModalComponent;

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

    getAdopterName(name : string) {
      if (name == null) {
        name = "pet.notAdopted";
      }
      return name;
    }

    getHostName(name : string) {
      if (name == null) {
        name = "pet.notHosted";
      }
      return name;
    }

    getPet() {
        this.petService.getPet(this.petId)
            .subscribe(
               result => {
                   this.pet = result.data;
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