import { Component, ViewChild } from '@angular/core';
import { Headers, RequestOptions, Http, Response } from '@angular/http';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';

// MODULES
import { TranslateService } from '@ngx-translate/core';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';

// INTERNAL
import { ProvinceService } from '../services/province.service';
import { ValidationService } from '../services/validation.service';
import { UserService } from '../services/user.service';
import { ErrorModalComponent } from '../modal/error-modal.component';
import * as $ from "jquery";

@Component({
  selector: 'menu',
  templateUrl: 'menu.html',
  providers: [ProvinceService, ValidationService]
})
export class MenuComponent {
	// Constructor
	constructor(fb: FormBuilder, translate: TranslateService, private http: Http, 
			private provinceService : ProvinceService, private validationService : ValidationService, 
			private userService : UserService ) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('es');
         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('es');
        this.translate = translate;
        
        // Formulario login
        this.loginForm = fb.group({
        	mail: [null, Validators.compose([Validators.required, validationService.email])],
        	password: [null, Validators.required]
        });
        this.loginMail = this.loginForm.controls['mail'];
        this.loginPass = this.loginForm.controls['password'];

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
    }

    ngAfterViewInit() {
    	let currentRoute = window.location.href.split('/')[window.location.href.split('/').length - 1];
    	// Active current menu
    	$('#menuBar button').removeClass('btn-success');
    	$('#menuBar button').addClass('btn-default');
    	$('#' + currentRoute + "Btn").addClass('btn-success');
    }

    // Variables del contexto
	@ViewChild(ErrorModalComponent)
	errorModal : ErrorModalComponent;

	@ViewChild('loginModal')
	loginModal : ModalComponent;

	@ViewChild('registerModal')
	registerModal : ModalComponent;

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
	badCredentials : String = null;
	duplicated : String = null;

	translate : TranslateService;

	// Meétodos
	changeMenu(id : string) {
		$('#menuBar button').removeClass('btn-success');
    	$('#menuBar button').addClass('btn-default');
    	$('#' + id).addClass('btn-success');
    }
	
	changeLanguage(lang : string) {
		this.translate.use(lang);
	}

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
						this.registerModal.close();
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

	submitLoginForm() {
		if (!this.loginForm.valid) {
			this.validationService.markFormAsTouched(this.loginForm);
		} else {
			let user : Object = this.loginForm.value;
			let result = this.userService.login(user).subscribe(
				result => {
					if (!result.success) {
						this.badCredentials = result.message;
					} else {
						this.loginModal.close();
						this.resetLoginForm();
						this.userService.loggedUser = result.data;
						alert(JSON.stringify(this.userService.loggedUser));
					}
				}, 
				error => {
					console.log(JSON.stringify(error.json()))
					return null;
				}
			);
		}
	}

	logout() {
		let result = this.userService.logout().subscribe(
			result => {
				if (!result) {
					this.errorModal.open('error.title', 'error.unexpected');
				} else {
					this.userService.loggedUser = null;
				}
			}, 
			error => {
				console.log(JSON.stringify(error.json()))
				return null;
			}
		);
	}

	resetLoginForm() {
		this.loginForm.reset();
		this.badCredentials = null;
	}

	resetRegisterForm() {
		this.registerForm.reset();
		this.province.setValue(0);
		this.association.setValue('false');
		this.passwords.reset();
		this.duplicated = null;
	}
}