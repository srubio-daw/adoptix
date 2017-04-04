import { Component, ViewContainerRef } from '@angular/core';
import { Headers, RequestOptions, Http, Response } from '@angular/http';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';

// MODULES
import { Md5 } from 'ts-md5/dist/md5';
import { TranslateService } from '@ngx-translate/core';

// INTERNAL
import { ProvinceService } from '../services/province.service';
import { ValidationService } from '../services/validation.service';

@Component({
  selector: 'menu',
  templateUrl: 'menu.html',
  providers: [ProvinceService, ValidationService]
})
export class MenuComponent {
	// Constructor
	constructor(fb: FormBuilder, translate: TranslateService, private http: Http, 
			private provinceService : ProvinceService, private validationService : ValidationService ) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('es');
         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('es');

        // Formulario login
        this.loginForm = fb.group({
        	mail: [null, Validators.compose([Validators.required, Validators.minLength(3), Validators.maxLength(150)])],
        	password: [null, Validators.required]
        });
        this.loginMail = this.loginForm.controls['mail'];
        this.loginPass = this.loginForm.controls['password'];

        // Formulario registro
        this.registerForm = fb.group({
        	association: ["no"],
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
    }

    // Variables del contexto
    loginForm : FormGroup;
    loginMail : AbstractControl;
    loginPass : AbstractControl;

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


	// Meétodos
	subscribeToAssociationChanges() {
		const associationChanges$ = this.association.valueChanges;

		associationChanges$.subscribe(value => {
			if (value == "yes") {
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

	submitRegisterForm(modal: any) {
		if (!this.registerForm.valid || !this.passwords.valid) {
			// Mark fields as dirty and return
			this.validationService.markFormAsTouched(this.registerForm);
			this.validationService.markFormAsTouched(this.passwords);
		} else {
			modal.close();
			this.registerForm.reset();
		}
	}

	submitLoginForm(modal: any) {
		if (!this.loginForm.valid) {
			this.validationService.markFormAsTouched(this.loginForm);
		} else {
			modal.close();
			alert("Email: " + this.loginForm.value.mail + ". Pass: " + Md5.hashStr(this.loginForm.value.password) );
			this.loginForm.reset();
		}
	}

	resetLoginForm() {
		this.loginForm.reset();
	}

	resetRegisterForm() {
		this.registerForm.reset();
		this.province.setValue(0);
		this.association.setValue('no');
		this.passwords.reset();
	}
}