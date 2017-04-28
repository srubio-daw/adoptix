import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { Md5 } from 'ts-md5/dist/md5';

@Injectable()
export class PetService {
	url : string = environment.apiUrl + "/pet";
	headers : Headers =  new Headers({ 'Content-Type': 'application/json' });
	formHeaders : Headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
	options : RequestOptions = new RequestOptions({ headers: this.headers });
	formOptions : RequestOptions = new RequestOptions({ headers: this.formHeaders });

	constructor (private http: Http) {}

	save(pet : Object, mail : string) {
		pet['userMail'] = mail;
		let body = JSON.stringify(pet);
		return this.http.post(this.url + "/create", body, this.options)
			.map(this.extractData);
	}

	private extractData(response : any) {
		let body = response.json();
		return body || [];
	}

	private responseOk(response : any) {
		return response.status == 200 ? true : false;
	}
}