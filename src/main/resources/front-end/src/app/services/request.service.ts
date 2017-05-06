import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { Md5 } from 'ts-md5/dist/md5';

@Injectable()
export class RequestService {
	url : string = environment.apiUrl + "/request";
	headers : Headers =  new Headers({ 'Content-Type': 'application/json' });
	formHeaders : Headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
	options : RequestOptions = new RequestOptions({ headers: this.headers });
	formOptions : RequestOptions = new RequestOptions({ headers: this.formHeaders });

	constructor (private http: Http) {}

	save(request : Object) {
		let body = JSON.stringify(request);
		return this.http.post(this.url, body, this.options)
			.map(this.extractData);
	}

	getPets(pagination : any, form : any) {
		let body = new URLSearchParams();
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		body.set('petType', form.petType != -1 ? form.petType : null);
		body.set('gender', form.gender != -1 ? form.gender : null);
		body.set('location', form.location != -1 ? form.location : null);
		body.set('minAge', form.minAge);
		body.set('maxAge', form.maxAge);
		body.set('dogsAffinity', form.dogsAffinity ? form.dogsAffinity : null);
		body.set('catsAffinity', form.catsAffinity ? form.catsAffinity : null);
		body.set('kidsAffinity', form.kidsAffinity ? form.kidsAffinity : null);
		return this.http.get(this.url, {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getByUserAndPet(userMail, petId, adoptOrHost) {
		let body = new URLSearchParams();
		body.set("userMail", userMail);
		body.set("petId", petId);
		body.set("adoptOrHost", adoptOrHost);
		return this.http.get(this.url + "/byUserAndPet", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getByPet(petId, pagination : any) {
		let body = new URLSearchParams();
		body.set("petId", petId);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/pet", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getManagedByPet(petId, pagination : any) {
		let body = new URLSearchParams();
		body.set("petId", petId);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/pet/managed", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getByUser(userMail, pagination : any) {
		let body = new URLSearchParams();
		body.set("mail", userMail);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/user", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getManagedByUser(userMail, pagination : any) {
		let body = new URLSearchParams();
		body.set("mail", userMail);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/user/managed", {search: body})
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