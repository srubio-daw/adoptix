import { Component, ViewContainerRef } from '@angular/core';
import { Headers, RequestOptions, Http, Response } from '@angular/http';

// MODULES
import { Md5 } from 'ts-md5/dist/md5';
import { TranslateService } from '@ngx-translate/core';

// INTERNAL
import { ProvinceService } from '../services/province.service';

@Component({
  selector: 'menu',
  templateUrl: 'menu.html',
  providers: [ProvinceService]
})
export class MenuComponent {
	constructor(translate: TranslateService, private http: Http, private provinceService : ProvinceService ) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('es');

         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('es');
    }

    ngOnInit() { this.getProvinces(); }

	public user : any = {};
	public provinces : any = [];

	public getProvinces() {
		this.provinceService.getProvinces()
            .subscribe(
               provinces => this.provinces = provinces,
               error =>  alert(error));
	}

	public registerUser(user: any, registerForm: any, modal: any) {
		if (registerForm.valid) {
			modal.close();
			let headers = new Headers({ 'Content-Type': 'application/json' });
    		let options = new RequestOptions({ headers: headers });
		} else {
			for (var property in registerForm.controls) {
				registerForm.controls[property].markAsDirty();
			}
		}
	}

	public loginUser(user: any, loginForm: any, modal: any) {
		if (loginForm.valid) {
			modal.close();
			alert("Email: " + user.mail + ". Pass: " + Md5.hashStr(user.password) );
			loginForm.reset();
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