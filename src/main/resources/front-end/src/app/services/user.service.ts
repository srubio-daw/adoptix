import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { environment } from '../../environments/environment';

import { Md5 } from 'ts-md5/dist/md5';

@Injectable()
export class UserService {
	loggedUser : any = null;

	url : string = environment.apiUrl + "/user";
	headers : Headers =  new Headers({ 'Content-Type': 'application/json' });
	options : RequestOptions = new RequestOptions({ headers: this.headers });

	constructor (private http: Http) {}

	register(user : Object) {
		user['password'] = Md5.hashStr(user['password']);
		let body = JSON.stringify(user);
		return this.http.post(this.url + "/create", body, this.options)
			.map(this.extractDataWithUser);
	}

	login(user : Object) {
		user['password'] = Md5.hashStr(user['password']);
		let body = JSON.stringify(user);
		return this.http.post(this.url + "/login", body, this.options)
			.map(this.extractDataWithUser);
	}

	logout() {
		return this.http.get(this.url + "/logout")
			.map(this.responseOk);
	}

	private extractDataWithUser(response : any) {
		let body = response.json();
		if (body && body.data != null) {
			this.loggedUser = body.data;
		}
		return body || [];
	}

	private responseOk(response : any) {
		return response.status == 200 ? true : false;
	}
}