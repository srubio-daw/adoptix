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

	getMyPets(mail : string, pagination : Object) {
		let body = new URLSearchParams();
		body.set('mail', mail);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/myPets", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	private extractData(response : any) {
		let body = response.json();
		return body || [];
	}

	private responseOk(response : any) {
		return response.status == 200 ? true : false;
	}

	private handleError (error: Response | any) {
	    // In a real world app, you might use a remote logging infrastructure
	    let errMsg: string;
		if (error instanceof Response) {
	 		const body = error.json() || '';
			const err = body.error || JSON.stringify(body);
			errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
	    } else {
	 		errMsg = error.message ? error.message : error.toString();
	    }
		console.error(errMsg);
		return Observable.throw(errMsg);
	}
}