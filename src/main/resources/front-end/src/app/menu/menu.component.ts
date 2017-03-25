import { Component, ViewContainerRef } from '@angular/core';

// MODULES
import {Md5} from 'ts-md5/dist/md5';

@Component({
  selector: 'menu',
  templateUrl: 'menu.html'
})
export class MenuComponent {
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