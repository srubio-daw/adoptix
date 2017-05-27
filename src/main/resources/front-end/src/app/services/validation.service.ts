import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
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

	matchingPasswords(passwordKey: string, passwordRepeatKey: string) {
		return (group: FormGroup) => {
			let password = group.controls[passwordKey];
			let confirmPassword = group.controls[passwordRepeatKey];

			if (password.value !== confirmPassword.value) {
				return {
					matchingPasswords: {
						valid: false
					}
				};
			}
		}
	}

	integer(c: FormControl) {
		let INTEGER_REGEXP = /^\d+$/;

		if (c.value == null) {
			return null;
		}

		return INTEGER_REGEXP.test(c.value) ? null : {
			integer: {
				valid: false
			}
		}
	}

	phone(c: FormControl) {
		let PHONE_REGEXP = /^\d{9}$/;

		if (c.value == null) {
			return null;
		}

		return PHONE_REGEXP.test(c.value) ? null : {
			phone: {
				valid: false
			}
		}
	}

	decimal(c: FormControl) {
		let DECIMAL_REGEXP = /^\d+([\.\,]\d{1,2})?$/;
		
		return DECIMAL_REGEXP.test(c.value) ? null : {
			decimal: {
				valid: false
			}
		}
	}

	comboSelected(c: FormControl) {
		return c.value !== 0 ? null : {
			comboSelected: {
				valid: false
			}
		}
	}

	markFormAsTouched(form : FormGroup) {
		for (var field in form.controls) {
			form.controls[field].markAsTouched();
			form.controls[field].updateValueAndValidity();
		}
		form.markAsTouched();
		form.updateValueAndValidity();
	}

}