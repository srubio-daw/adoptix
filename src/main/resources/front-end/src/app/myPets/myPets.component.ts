import { Component, ViewChild } from '@angular/core';

// INTERNO
import { PetService } from '../services/pet.service';
import { UserService } from '../services/user.service';
import { ErrorModalComponent } from '../modal/error-modal.component';
import * as $ from "jquery";

@Component({
  templateUrl: 'myPets.html',
  providers: [PetService]
})
export class MyPetsComponent {
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
  	this.getMyPets();
  }

  getMyPets() {
  	this.petService.getMyPets(this.userService.loggedUser.name, this.pagination)
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

  getCheckboxIcon(status : boolean) {
    if (status) {
      return "glyphicon glyphicon-check";
    } else {
      return "glyphicon glyphicon-unchecked";
    }
  }

  deletePet(petId : number) {
    this.petService.deletePet(petId)
        .subscribe(
            result => {
                if (result.success) {
                    this.getMyPets();
                } else {
                    this.errorModal.open('error.title', result.message);
                }
            },
            error => alert(error)
        );
  }

  refreshTable(rowsChanged : boolean) {
    if (rowsChanged) {
      this.pagination.page = 1;
    }
    this.getMyPets();
  }
}