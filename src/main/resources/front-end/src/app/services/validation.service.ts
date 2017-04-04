import { AbstractControl, FormControl } from '@angular/forms';
import { Injectable } from '@angular/core'

@Injectable()
export class ValidationService {

	email(c: FormControl) {
		let EMAIL_REGEXP = /^[a-zA-Z0-9_\.-]+@[a-zA-Z0-9_\.-]+\.[a-zA-Z]+$/;

		return EMAIL_REGEXP.test(c.value) ? null : {
			email: {
				valid: false
			}
		};
	}

	passwordRepeat = (password : AbstractControl) => {
		return (repeatPassword: FormControl) => {
			if (password.touched && repeatPassword.touched && password.value != repeatPassword.value) {
				return {
					repeatPassword: {
						valid: false
					}
				}
			}
			return null;
		}
	};

}