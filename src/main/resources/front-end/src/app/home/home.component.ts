import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';

// INTERNO
import { PetService } from '../services/pet.service';
import { UserService } from '../services/user.service';
import { ErrorModalComponent } from '../modal/error-modal.component';
import { ProvinceService } from '../services/province.service';
import { ValidationService } from '../services/validation.service'

@Component({
  templateUrl: 'home.html',
  providers: [PetService, ProvinceService, ValidationService]
})
export class HomeComponent {
  constructor(fb: FormBuilder, private petService : PetService, private userService : UserService, 
  		private provinceService : ProvinceService, private validationService : ValidationService,) { 
  	this.searchForm = fb.group({
  		petType: [-1],
  		gender: [-1],
  		dogsAffinity: [null],
  		catsAffinity: [null],
  		kidsAffinity: [null],
  		location: [-1],
  		minAge: [null, validationService.integer],
  		maxAge: [null, validationService.integer]
  	});
  	this.petType = this.searchForm.controls['petType'];
  	this.gender = this.searchForm.controls['gender'];
  	this.dogsAffinity = this.searchForm.controls['dogsAffinity'];
  	this.catsAffinity = this.searchForm.controls['catsAffinity'];
  	this.kidsAffinity = this.searchForm.controls['kidsAffinity'];
  	this.location = this.searchForm.controls['location'];
  	this.minAge = this.searchForm.controls['minAge'];
  	this.maxAge = this.searchForm.controls['maxAge'];
  }

  // Search Form
  searchForm : FormGroup;
  petType : AbstractControl;
  gender : AbstractControl;
  dogsAffinity : AbstractControl;
  catsAffinity : AbstractControl;
  kidsAffinity : AbstractControl;
  location : AbstractControl;
  minAge : AbstractControl;
  maxAge : AbstractControl;

  provinces : any = [];
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
  	this.getProvinces();
  }

  getProvinces() {
		this.provinceService.getProvinces()
            .subscribe(
               provinces => this.provinces = provinces,
               error =>  alert(error));
	}

  getPets() {
  	this.petService.getPets(this.pagination, this.searchForm.value)
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