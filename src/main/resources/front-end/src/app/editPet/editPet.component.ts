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
  templateUrl: '../newPet/newPet.html',
  providers: [ValidationService, ProvinceService, PetService, RequestService]
})
export class EditPetComponent {
	constructor(fb: FormBuilder, private validationService : ValidationService, private userService : UserService,
		private provinceService : ProvinceService, private petService : PetService, private activatedRoute: ActivatedRoute,
        private requestService : RequestService) {
		 // Formulario registro
        this.petForm = fb.group({
            id: [null],
            gender: [null, Validators.required],
        	name: [null, Validators.required],
        	petType: [null, Validators.required],
        	breed: [null, Validators.required],
        	age: [null, Validators.compose([Validators.required, validationService.integer])],
        	adopter: [null],
        	host: [null],
        	locationId: [null, validationService.comboSelected],
        	kidsAffinity: [null],
        	dogsAffinity: [null],
        	catsAffinity: [null],
        	forAdoption: [null],
        	forHost: [null],
        	description: [null],
            image: [null]
        });
        this.id = this.petForm.controls['id'];
        this.gender = this.petForm.controls['gender'];
	    this.name = this.petForm.controls['name'];
	    this.petType = this.petForm.controls['petType'];
	    this.breed = this.petForm.controls['breed'];
	    this.age = this.petForm.controls['age'];
	    this.adopter = this.petForm.controls['adopter'];
	    this.host = this.petForm.controls['host'];
	    this.locationId = this.petForm.controls['locationId'];
	    this.kidsAffinity = this.petForm.controls['kidsAffinity'];
	    this.dogsAffinity = this.petForm.controls['dogsAffinity'];
	    this.catsAffinity = this.petForm.controls['catsAffinity'];
	    this.forAdoption = this.petForm.controls['forAdoption'];
	    this.forHost = this.petForm.controls['forHost'];
	    this.description = this.petForm.controls['description'];
        this.image = this.petForm.controls['image'];

        // Formulario de vacunas
        this.vaccineForm = fb.group({
            id : [null],
            petId : [null],
            name: [null, Validators.required],
            description: [null],
            appliedOn: [null, Validators.required]
        });
        this.vaccineId = this.vaccineForm.controls['id'];
        this.vaccinePetId = this.vaccineForm.controls['petId'];
        this.vaccineName = this.vaccineForm.controls['name'];
        this.vaccineDescription = this.vaccineForm.controls['description'];
        this.vaccineAppliedOn = this.vaccineForm.controls['appliedOn'];

        // Formulario de visitas veterinarias
        this.vetVisitForm = fb.group({
            id : [null],
            petId : [null],
            description: [null, Validators.required],
            cost: [0.00, Validators.compose([validationService.decimal, Validators.required])],
            visitDate: [null, Validators.required]
        });
        this.vetVisitId = this.vetVisitForm.controls['id'];
        this.vetVisitPetId = this.vetVisitForm.controls['petId'];
        this.vetVisitCost = this.vetVisitForm.controls['cost'];
        this.vetVisitDescription = this.vetVisitForm.controls['description'];
        this.vetVisitVisitDate = this.vetVisitForm.controls['visitDate'];

        // Formulario de pruebas médicas
        this.medicalTestForm = fb.group({
            id : [null],
            petId : [null],
            name: [null, Validators.required],
            description: [null],
            appliedOn: [null, Validators.required]
        });
        this.medicalTestId = this.medicalTestForm.controls['id'];
        this.medicalTestPetId = this.medicalTestForm.controls['petId'];
        this.medicalTestName = this.medicalTestForm.controls['name'];
        this.medicalTestDescription = this.medicalTestForm.controls['description'];
        this.medicalTestAppliedOn = this.medicalTestForm.controls['appliedOn'];

        this.rejectRequestForm = fb.group({
            comment: [null]
        });
        this.rejectRequestComment = this.rejectRequestForm.controls['comment'];

    }

    // Inicialización
    ngOnInit() { 
    	this.getProvinces();

        this.activatedRoute.params.subscribe((params: Params) => {
            this.petId = params['petId'];
            this.imageUrl = this.petService.getImageUrl(this.petId);
            this.getNormalUsers();
            this.getPetVetVisits(false);
            this.getPetVaccines(false);
            this.getPetMedicalTests(false);
            this.getPendingRequests(false);
            this.getManagedRequests(false);
        });
    }

    petForm : FormGroup;
    id : AbstractControl
    name : AbstractControl;
    gender : AbstractControl;
    petType : AbstractControl;
    breed : AbstractControl;
    age : AbstractControl;
    adopter : AbstractControl;
    host : AbstractControl;
    locationId : AbstractControl;
    kidsAffinity : AbstractControl;
    dogsAffinity : AbstractControl;
    catsAffinity : AbstractControl;
    forAdoption : AbstractControl;
    forHost : AbstractControl;
    description : AbstractControl;
    image : AbstractControl;

    imageFile : any = null;
    imageUrl : string = null;
    errorFileType : boolean = false;

    vaccineForm : FormGroup;
    vaccineId : AbstractControl;
    vaccinePetId : AbstractControl;
    vaccineName : AbstractControl;
    vaccineDescription : AbstractControl;
    vaccineAppliedOn : AbstractControl;

    vetVisitForm : FormGroup;
    vetVisitId : AbstractControl;
    vetVisitPetId : AbstractControl;
    vetVisitCost : AbstractControl;
    vetVisitDescription : AbstractControl;
    vetVisitVisitDate : AbstractControl;

    medicalTestForm : FormGroup;
    medicalTestId : AbstractControl;
    medicalTestPetId : AbstractControl;
    medicalTestName : AbstractControl;
    medicalTestDescription : AbstractControl;
    medicalTestAppliedOn : AbstractControl;

    rejectRequestForm : FormGroup;
    rejectRequestComment : AbstractControl;
    requestToReject : any = null;

    normalUsers : Array<any> = [];
    provinces : Array<any> = [];

    @ViewChild(ErrorModalComponent)
	errorModal : ErrorModalComponent;

    @ViewChild('vaccineModal')
    vaccineModal : ModalComponent;  

    @ViewChild('medicalTestModal')
    medicalTestModal : ModalComponent;

    @ViewChild('vetVisitModal')
    vetVisitModal : ModalComponent;

    @ViewChild('rejectRequestModal')
    rejectRequestModal : ModalComponent;

    // Reutilización de vista
    headingTitle : string = "pet.editPet";
    showAdditionalInfo : boolean = true;
    editEnabled : boolean = false;

    modalTitle : string = null;

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
    pendingRequests : any = [];
    pendingRequestsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };
    managedRequests : any = [];
    managedRequestsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };

    accept(request : any) {
        request.status = true;
        let result = this.requestService.save(request).subscribe(
          result => {
            if (!result.success) {
              let title = null;
              this.errorModal.open('error.title', result.message);
            } else {
              this.getPendingRequests(true);
              this.getManagedRequests(true);
              this.getPet();
            }
          }, 
          error => {
            console.log(JSON.stringify(error.json()))
            return null;
          }
        );
    } 

    reject(request : any) {
        this.requestToReject = request;
        this.rejectRequestModal.open();
    } 

    submitRejectRequestForm() {
        var comment = this.rejectRequestComment.value;
        let body = this.requestToReject;
        body.rejectComment = comment;
        body.status = false;
        let result = this.requestService.save(body).subscribe(
          result => {
            if (!result.success) {
              let title = null;
              this.errorModal.open('error.title', result.message);
            } else {
              this.getPendingRequests(true);
              this.getManagedRequests(true);
              this.rejectRequestModal.close();
              this.requestToReject = null;
            }
          }, 
          error => {
            console.log(JSON.stringify(error.json()))
            return null;
          }
        );
    }

    getRequestType(type : boolean) {
        if (type) {
            return "request.adoption";
        } else {
            return "request.host";
        }
    }

    getStatus(status : boolean) {
        if (status) {
            return "request.accepted";
        } else {
            return "request.rejected";
        }
    }

    getNormalUsers() {
		this.userService.getNormalUsers()
            .subscribe(
               result => {
                   this.normalUsers = result.data;
                   this.getPet();
               },
               error =>  alert(error));
	}

	getProvinces() {
		this.provinceService.getProvinces()
            .subscribe(
               provinces => this.provinces = provinces,
               error =>  alert(error));
	}

    isValidFile(type : string) {
        if (type.split("/")[0] != "image") {
            return false;
        }
        return true;
    }

    onChangeImage(event) {
        this.imageFile = event.srcElement.files[0];
        this.errorFileType = !this.isValidFile(this.imageFile.type);
    }

	savePet() {
		if (!this.petForm.valid || (!this.showAdditionalInfo && (this.imageFile == null || !this.isValidFile(this.imageFile.type)))) {
			// Mark fields as dirty and return
			this.validationService.markFormAsTouched(this.petForm);
		} else {
			let pet : Object = this.petForm.value;
			let result = this.petService.save(pet, this.userService.loggedUser['name'], this.imageFile).subscribe(
				result => {
					if (!result.success) {
						this.errorModal.open('error.title', result.message);
					} else {
                        this.editEnabled = false;
                        this.imageFile = null;
                        this.image.setValue("");
                        this.imageUrl = this.petService.getImageUrl(this.petId) + '?random=' + Math.random();
					}
				}, 
				error => {
					console.log(JSON.stringify(error.json()))
					return null;
				}
			);
		}
	}

    setPetFormValues() {
        this.id.setValue(this.pet.id);
        this.name.setValue(this.pet.name);
        this.gender.setValue(this.pet.gender);
        this.breed.setValue(this.pet.breed);
        this.age.setValue(this.pet.age);
        this.petType.setValue(this.pet.petType.toString());
        this.forAdoption.setValue(this.pet.forAdoption);
        this.forHost.setValue(this.pet.forHost);
        this.locationId.setValue(this.pet.locationId);
        if (this.pet.adopter != null) {
           this.adopter.setValue(this.pet.adopter.toString());
        }
        if (this.pet.host != null) {
           this.host.setValue(this.pet.host.toString());
        }
        this.description.setValue(this.pet.description);
        this.dogsAffinity.setValue(this.pet.dogsAffinity);
        this.catsAffinity.setValue(this.pet.catsAffinity);
        this.kidsAffinity.setValue(this.pet.kidsAffinity);
    }

	resetPetForm() {
		this.setPetFormValues();
        this.editEnabled = false;
	}

    getPet() {
        this.petService.getPet(this.petId)
            .subscribe(
               result => {
                   this.pet = result.data;
                   this.setPetFormValues();
               },
               error =>  alert(error));
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

    getPendingRequests(rowsChange : boolean) {
        if (rowsChange) {
            this.pendingRequestsPagination.page = 1;
        }
        this.requestService.getByPet(this.petId, this.pendingRequestsPagination)
            .subscribe(
               result => {
                   this.pendingRequests = result.data;
                   this.pendingRequestsPagination.totalRecords = result.totalRecords;
                   this.pendingRequestsPagination.pages = this.getPages(this.pendingRequestsPagination);
               },
               error =>  alert(error)
        );
    }

    getManagedRequests(rowsChange : boolean) {
        if (rowsChange) {
            this.managedRequestsPagination.page = 1;
        }
        this.requestService.getManagedByPet(this.petId, this.managedRequestsPagination)
            .subscribe(
               result => {
                   this.managedRequests = result.data;
                   this.managedRequestsPagination.totalRecords = result.totalRecords;
                   this.managedRequestsPagination.pages = this.getPages(this.managedRequestsPagination);
               },
               error =>  alert(error)
        );
    }

    deleteVaccine(vaccineId : number) {
        this.petService.deleteVaccine(vaccineId)
            .subscribe(
                result => {
                    if (result.success) {
                        this.getPetVaccines(false);
                    } else {
                        this.errorModal.open('error.title', result.message);
                    }
                },
                error => alert(error)
            );
    }

    deleteVetVisit(vetVisitId : number) {
        this.petService.deleteVetVisit(vetVisitId)
            .subscribe(
                result => {
                    if (result.success) {
                        this.getPetVetVisits(false);
                    } else {
                        this.errorModal.open('error.title', result.message);
                    }
                },
                error => alert(error)
            );
    }

    deleteMedicalTest(medicalTestId : number) {
        this.petService.deleteMedicalTest(medicalTestId)
            .subscribe(
                result => {
                    if (result.success) {
                        this.getPetMedicalTests(false);
                    } else {
                        this.errorModal.open('error.title', result.message);
                    }
                },
                error => alert(error)
            );
    }

    editVaccine(vaccine : any) {
        this.modalTitle = "pet.editVaccine";
        this.vaccineId.setValue(vaccine.id);
        this.vaccinePetId.setValue(vaccine.petId);
        this.vaccineName.setValue(vaccine.name);
        this.vaccineDescription.setValue(vaccine.description);
        this.vaccineAppliedOn.setValue(vaccine.appliedOn);
        this.vaccineModal.open();
    }

    editVetVisit(vetVisit : any) {
        this.modalTitle = "pet.editVetVisit";
        this.vetVisitId.setValue(vetVisit.id);
        this.vetVisitPetId.setValue(vetVisit.petId);
        this.vetVisitCost.setValue(vetVisit.cost);
        this.vetVisitDescription.setValue(vetVisit.description);
        this.vetVisitVisitDate.setValue(vetVisit.visitDate);
        this.vetVisitModal.open();
    }

    editMedicalTest(medicalTest : any) {
        this.modalTitle = "pet.editMedicalTest";
        this.medicalTestId.setValue(medicalTest.id);
        this.medicalTestPetId.setValue(medicalTest.petId);
        this.medicalTestName.setValue(medicalTest.name);
        this.medicalTestDescription.setValue(medicalTest.description);
        this.medicalTestAppliedOn.setValue(medicalTest.appliedOn);
        this.medicalTestModal.open();
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

    openModal(modal : ModalComponent) {
        if (modal == this.vaccineModal) {
            this.modalTitle = "pet.newVaccine";
        } else if (modal == this.medicalTestModal) {
            this.modalTitle = "pet.newMedicalTest";
        } else {
            this.modalTitle = "pet.newVetVisit";
        }
        modal.open();
    }

    resetForm(form : FormGroup) {
        form.reset();
    }

    submitVaccineForm() {
        if (!this.vaccineForm.valid) {
            // Mark fields as dirty and return
            this.validationService.markFormAsTouched(this.vaccineForm);
        } else {
            let vaccine = this.vaccineForm.value;
            let petId = this.pet.id;
            let result = this.petService.saveVaccine(petId, vaccine).subscribe(
                result => {
                    if (!result.success) {
                        this.errorModal.open('error.title', result.message);
                    } else {
                        this.vaccineForm.reset();
                        this.vaccineModal.dismiss();
                        this.getPetVaccines(false);
                    }
                }, 
                error => {
                    console.log(JSON.stringify(error.json()))
                    return null;
                }
            );
        }
    }

    submitVetVisitForm() {
         if (!this.vetVisitForm.valid) {
            // Mark fields as dirty and return
            this.validationService.markFormAsTouched(this.vetVisitForm);
        } else {
            let vetVisit = this.vetVisitForm.value;
            let petId = this.pet.id;
            let result = this.petService.saveVetVisit(petId, vetVisit).subscribe(
                result => {
                    if (!result.success) {
                        this.errorModal.open('error.title', result.message);
                    } else {
                        this.vetVisitForm.reset();
                        this.vetVisitModal.dismiss();
                        this.getPetVetVisits(false);
                        this.vetVisitCost.setValue(0);
                    }
                }, 
                error => {
                    console.log(JSON.stringify(error.json()))
                    return null;
                }
            );
        }  
    }

    submitMedicalTestForm() {
        if (!this.medicalTestForm.valid) {
            // Mark fields as dirty and return
            this.validationService.markFormAsTouched(this.medicalTestForm);
        } else {
            let medicalTest = this.medicalTestForm.value;
            let petId = this.pet.id;
            let result = this.petService.saveMedicalTest(petId, medicalTest).subscribe(
                result => {
                    if (!result.success) {
                        this.errorModal.open('error.title', result.message);
                    } else {
                        this.medicalTestForm.reset();
                        this.medicalTestModal.dismiss();
                        this.getPetMedicalTests(false);
                    }
                }, 
                error => {
                    console.log(JSON.stringify(error.json()))
                    return null;
                }
            );
        }
    }
}