import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { Md5 } from 'ts-md5/dist/md5';

@Injectable()
export class UserService {
	url : string = environment.apiUrl + "/user";
	headers : Headers =  new Headers({ 'Content-Type': 'application/json' });
	formHeaders : Headers = new Headers({'Content-Type': 'application/x-www-form-urlencoded'});
	options : RequestOptions = new RequestOptions({ headers: this.headers });
	formOptions : RequestOptions = new RequestOptions({ headers: this.formHeaders });

	// Testing
	loggedUser : Object = {"authorities":[{"authority":"usuario"}],"details":null,"authenticated":true,"principal":{"password":null,"username":"sylrupenopa@gmail.com","authorities":[{"authority":"usuario"}],"accountNonExpired":true,"accountNonLocked":true,"credentialsNonExpired":true,"enabled":true},"credentials":null,"name":"sylrupenopa@gmail.com"};
	//loggedUser : Object = null;

	constructor (private http: Http) {}

	register(user : Object) {
		user['password'] = Md5.hashStr(user['password']);
		let body = JSON.stringify(user);
		return this.http.post(this.url + "/create", body, this.options)
			.map(this.extractData);
	}

	login(user : Object) {
		user['password'] = Md5.hashStr(user['password']);
		let body = JSON.stringify(user);
		return this.http.post(this.url + "/login", body, this.options)
			.map(this.extractData);
	}

	logout() {
		return this.http.get(this.url + "/logout")
			.map(this.responseOk);
	}

	getUserData() {
		let body = new URLSearchParams();
		body.set('mail', this.loggedUser['name']);
		return this.http.post(this.url, body, this.formOptions)
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