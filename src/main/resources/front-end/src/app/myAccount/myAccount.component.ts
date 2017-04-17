import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';

// MODULES
import { TranslateService } from '@ngx-translate/core';

// INTERNAL
import { ProvinceService } from '../services/province.service';
import { ValidationService } from '../services/validation.service';
import { UserService } from '../services/user.service';
import { ErrorModalComponent } from '../modal/error-modal.component';

@Component({
  templateUrl: 'myAccount.html',
  providers: [ProvinceService, ValidationService]
})
export class MyAccountComponent {
	constructor(fb: FormBuilder, translate: TranslateService, private validationService : ValidationService, 
		private userService : UserService, private provinceService : ProvinceService ) {
		 // Formulario registro
        this.registerForm = fb.group({
        	association: ["false"],
        	name: [null, Validators.required],
        	surname: [null, Validators.required],
        	nif: [null],
        	address: [null, Validators.required],
        	province: [0, validationService.comboSelected],
        	mail: [null, Validators.compose([Validators.required, validationService.email])],
        	username: [null, Validators.required]
        });
	    this.association = this.registerForm.controls['association'];
	    this.name = this.registerForm.controls['name'];
	    this.surname = this.registerForm.controls['surname'];
	    this.nif = this.registerForm.controls['nif'];
	    this.address = this.registerForm.controls['address'];
	    this.province = this.registerForm.controls['province'];
	    this.username = this.registerForm.controls['username'];
	    this.registerMail = this.registerForm.controls['mail'];
   		// Grupo de contraseñas
   		this.passwords = fb.group({
   			password: [null, Validators.required],
        	passwordRepeat: [null, Validators.required]
   		}, {validator: validationService.matchingPasswords('password', 'passwordRepeat')});
   		this.registerPass = this.passwords.controls['password'];
   		this.passRepeat = this.passwords.controls['passwordRepeat'];
	}

	// Inicialización
    ngOnInit() { 
    	this.getProvinces();
    	this.subscribeToAssociationChanges();
    	this.getUserData();
    }

	registerForm : FormGroup;
    association : AbstractControl;
    name : AbstractControl;
    surname : AbstractControl;
    nif : AbstractControl;
    address : AbstractControl;
    province : AbstractControl;
    username : AbstractControl;
    registerMail : AbstractControl;
    registerPass : AbstractControl;
    passRepeat : AbstractControl;
    passwords : FormGroup;

	provinces : any = [];
	user : any = null;
	duplicated : String = null;

	@ViewChild(ErrorModalComponent)
	errorModal : ErrorModalComponent;

	// Meétodos
	subscribeToAssociationChanges() {
		const associationChanges$ = this.association.valueChanges;

		associationChanges$.subscribe(value => {
			if (value == "true") {
				this.nif.setValidators(Validators.required);
				this.nif.updateValueAndValidity();
				this.surname.setValue(null);
				this.surname.setValidators(null);
				this.surname.updateValueAndValidity();
			} else {
				this.nif.setValue(null);
				this.nif.setValidators(null);
				this.nif.updateValueAndValidity();
				this.surname.setValidators(Validators.required);
				this.surname.updateValueAndValidity();
			}
		});
	}

	getProvinces() {
		this.provinceService.getProvinces()
            .subscribe(
               provinces => this.provinces = provinces,
               error =>  alert(error));
	}

	getUserData() {
		this.userService.getUserData()
			.subscribe(
				user => this.user = user,
				error => alert(error));
	}

	submitRegisterForm() {
		if (!this.registerForm.valid || !this.passwords.valid) {
			// Mark fields as dirty and return
			this.validationService.markFormAsTouched(this.registerForm);
			this.validationService.markFormAsTouched(this.passwords);
		} else {
			let user : Object = this.registerForm.value;
			for (var property in this.passwords.value) {
				user[property] = this.passwords.value[property];
			}
			let result = this.userService.register(user).subscribe(
				result => {
					if (!result.success) {
						if (result.message.indexOf('error.duplicated') != -1) {
							this.duplicated = result.message;
						} else {
							this.errorModal.open('error.title', result.message);
						}
					} else {
						this.resetRegisterForm();
						this.userService.loggedUser = result.data;
					}
				}, 
				error => {
					console.log(JSON.stringify(error.json()))
					return null;
				}
			);
		}
	}

	resetRegisterForm() {
		this.registerForm.reset();
		this.province.setValue(0);
		this.association.setValue('no');
		this.passwords.reset();
		this.duplicated = null;
	}
}