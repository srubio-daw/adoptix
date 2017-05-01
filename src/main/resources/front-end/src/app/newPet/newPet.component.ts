import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

// INTERNAL
import { ValidationService } from '../services/validation.service';
import { UserService } from '../services/user.service';
import { ProvinceService } from '../services/province.service';
import { PetService } from '../services/pet.service';
import { ErrorModalComponent } from '../modal/error-modal.component';

@Component({
  templateUrl: 'newPet.html',
  providers: [ValidationService, ProvinceService, PetService]
})
export class NewPetComponent {
	constructor(fb: FormBuilder, private validationService : ValidationService, private userService : UserService,
		private provinceService : ProvinceService, private petService : PetService, private router : Router) {
		 // Formulario registro
        this.petForm = fb.group({
        	name: [null, Validators.required],
            gender: [null, Validators.required],
        	petType: [null, Validators.required],
        	breed: [null, Validators.required],
        	age: [null, Validators.compose([Validators.required, validationService.integer])],
        	adopter: [null],
        	host: [null],
        	locationId: [0, validationService.comboSelected],
        	kidsAffinity: [null],
        	dogsAffinity: [null],
        	catsAffinity: [null],
        	forAdoption: [null],
        	forHost: [null],
        	description: [null]
        });
	    this.name = this.petForm.controls['name'];
        this.gender = this.petForm.controls['gender'];
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
    }

    // Inicialización
    ngOnInit() { 
    	this.getNormalUsers();
    	this.getProvinces();
    }

    petForm : FormGroup;
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

    normalUsers : Array<any> = [];
    provinces : Array<any> = [];

    @ViewChild(ErrorModalComponent)
	errorModal : ErrorModalComponent;

    // Reutilización de vista
    headingTitle : string = "menu.newPet";
    showAdditionalInfo : boolean = false;
    editEnabled : boolean = true;

    getNormalUsers() {
		this.userService.getNormalUsers()
            .subscribe(
               result => this.normalUsers = result.data,
               error =>  alert(error));
	}

	getProvinces() {
		this.provinceService.getProvinces()
            .subscribe(
               provinces => this.provinces = provinces,
               error =>  alert(error));
	}

	savePet() {
		if (!this.petForm.valid) {
			// Mark fields as dirty and return
			this.validationService.markFormAsTouched(this.petForm);
		} else {
			let pet : Object = this.petForm.value;
			let result = this.petService.save(pet, this.userService.loggedUser['name']).subscribe(
				result => {
					if (!result.success) {
						this.errorModal.open('error.title', result.message);
					} else {
                        // Redirijo a la pantalla de edición
                        let petId = result.data;
                        this.router.navigateByUrl("/association/editPet/" + petId);
					}
				}, 
				error => {
					console.log(JSON.stringify(error.json()))
					return null;
				}
			);
		}
	}

	resetPetForm() {
		this.petForm.reset();
		this.locationId.setValue(0);
	}
}