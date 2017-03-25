import { Component, ViewContainerRef } from '@angular/core';

// MODULES
import { Md5 } from 'ts-md5/dist/md5';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'menu',
  templateUrl: 'menu.html'
})
export class MenuComponent {
	constructor(translate: TranslateService) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('es');

         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('es');
    }

	public user : any = {};
	public provinces : any = [];

	public registerUser(user: any, registerForm: any, modal: any) {
		if (registerForm.valid) {
			modal.close();
			registerForm.reset();
			alert(JSON.stringify(user));
		} else {
			for (var property in registerForm.controls) {
				registerForm.controls[property].markAsDirty();
			}
		}
	}

	public loginUser(user: any, loginForm: any, modal: any) {
		if (loginForm.valid) {
			modal.close();
			loginForm.reset();
			alert("Email: " + user.mail + ". Pass: " + Md5.hashStr(user.password) );
		} else {
			for (var property in loginForm.controls) {
				loginForm.controls[property].markAsDirty();
			}
		}
	}

	public resetForm(form : any) {
		form.reset();
	}
}