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
		return this.http.post(this.url + "/save", body, this.options)
			.map(this.extractData);
	}

	saveVaccine(petId : number, vaccine : any) {
		let body = JSON.stringify(vaccine);
		return this.http.post(this.url + "/" + petId + "/vaccine", body, this.options)
			.map(this.extractData);
	}

	deleteVaccine(vaccineId : number) {
		return this.http.get(this.url + "/deleteVaccine/" + vaccineId)
			.map(this.extractData);
	}

	saveMedicalTest(petId : number, medicalTest : any) {
		let body = JSON.stringify(medicalTest);
		return this.http.post(this.url + "/" + petId + "/medicalTest", body, this.options)
			.map(this.extractData);
	}

	deleteMedicalTest(medicalTestId : number) {
		return this.http.get(this.url + "/deleteMedicalTest/" + medicalTestId)
			.map(this.extractData);
	}

	saveVetVisit(petId : number, vetVisit : any) {
		let body = JSON.stringify(vetVisit);
		return this.http.post(this.url + "/" + petId + "/vetVisit", body, this.options)
			.map(this.extractData);
	}

	deleteVetVisit(vetVisitId : number) {
		return this.http.get(this.url + "/deleteVetVisit/" + vetVisitId)
			.map(this.extractData);
	}

	getMyPets(mail : string, pagination : any) {
		let body = new URLSearchParams();
		body.set('mail', mail);
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/myPets", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
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
		return this.http.get(this.url + "/", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getPet(petId : number) {
		let body = new URLSearchParams();
		return this.http.get(this.url + "/" + petId, {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	deletePet(petId : number) {
		return this.http.get(this.url + "/" + petId + "/delete")
			.map(this.extractData);
	}

	getPetVaccines(petId : number, pagination : any) {
		let body = new URLSearchParams();
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/" + petId + "/vaccines", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getPetMedicalTests(petId : number, pagination : any) {
		let body = new URLSearchParams();
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/" + petId + "/medicalTests", {search: body})
			.map(this.extractData)
			.catch(this.handleError);
	}

	getPetVetVisits(petId : number, pagination : any) {
		let body = new URLSearchParams();
		body.set('page', pagination.page);
		body.set('rows', pagination.rows);
		return this.http.get(this.url + "/" + petId + "/vetVisits", {search: body})
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