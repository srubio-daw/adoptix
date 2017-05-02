import { Component, ViewChild } from '@angular/core';

// INTERNO
import { PetService } from '../services/pet.service';
import { UserService } from '../services/user.service';
import { ErrorModalComponent } from '../modal/error-modal.component';
import * as $ from "jquery";

@Component({
  templateUrl: 'home.html',
  providers: [PetService]
})
export class HomeComponent {
	constructor(private petService : PetService, private userService : UserService) { }

  pets : any = [];
  pagination : any = {
    page: 1,
    rows: 20,
    totalRecords: 0,
    pages: []
  };

  @ViewChild(ErrorModalComponent)
  errorModal : ErrorModalComponent;
 
  ngOnInit() {
  	this.getPets();
  }

  getPets() {
  	this.petService.getPets(this.pagination)
          .subscribe(
             result => {
             		this.pets = result.data;
                this.pagination.totalRecords = result.totalRecords;
                this.pagination.pages = this.getPages();
             	},
             error =>  alert(error));
  }

  getPages() {
    if (this.pagination.totalRecords == 0) {
      return [];
    } else {
      let records = this.pagination.totalRecords;
      let pages = [];
      for (let i = 0; records > 0; i++) {
        pages[i] = i + 1;
        records = records - this.pagination.rows;
      }
      return pages;
    }
  }

  getPetTypeLabel(petType : number) {
    switch (petType) {
      case 1:
        return "pet.dog";
      case 2:
        return "pet.cat";
      default:
        return "Pet type undefined";
    }
  }

  getGenderIcon(gender : string) {
  	if (gender == "f") {
  		return "fa fa-venus";
  	} else {
  		return "fa fa-mars";
  	}
  }

  getCheckboxIcon(status : boolean) {
    if (status) {
      return "glyphicon glyphicon-check";
    } else {
      return "glyphicon glyphicon-unchecked";
    }
  }

  refreshTable(rowsChanged : boolean) {
    if (rowsChanged) {
      this.pagination.page = 1;
    }
    this.getPets();
  }
}