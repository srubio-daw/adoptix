import { Component, ViewChild } from '@angular/core';

// MODULOS
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';

// INTERNAL
import { UserService } from '../services/user.service';
import { RequestService } from '../services/request.service';
import { ErrorModalComponent } from '../modal/error-modal.component';

@Component({
  templateUrl: 'requests.html',
  providers: [RequestService]
})
export class RequestsComponent {
	constructor(private userService : UserService, private requestService : RequestService) { }

    // Inicialización
    ngOnInit() { 
        this.getPendingRequests(false);
        this.getManagedRequests(false);
    }

    @ViewChild(ErrorModalComponent)
	  rrorModal : ErrorModalComponent;

    pendingRequests : any = [];
    pendingRequestsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };
    managedRequests : any = [];
    managedRequestsPagination : any = {
        page: 1,
        rows: 5,
        totalRecords: 0,
        pages: []
    };

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

    getRequestType(type : boolean) {
        if (type) {
            return "request.adoption";
        } else {
            return "request.host";
        }
    }

    getStatus(status : boolean) {
        if (status) {
            return "request.accepted";
        } else {
            return "request.rejected";
        }
    }

    getPendingRequests(rowsChange : boolean) {
        if (rowsChange) {
            this.pendingRequestsPagination.page = 1;
        }
        this.requestService.getByUser(this.userService.loggedUser.name, this.pendingRequestsPagination)
            .subscribe(
               result => {
                   this.pendingRequests = result.data;
                   this.pendingRequestsPagination.totalRecords = result.totalRecords;
                   this.pendingRequestsPagination.pages = this.getPages(this.pendingRequestsPagination);
               },
               error =>  alert(error)
        );
    }

    getManagedRequests(rowsChange : boolean) {
        if (rowsChange) {
            this.managedRequestsPagination.page = 1;
        }
        this.requestService.getManagedByUser(this.userService.loggedUser.name, this.managedRequestsPagination)
            .subscribe(
               result => {
                   this.managedRequests = result.data;
                   this.managedRequestsPagination.totalRecords = result.totalRecords;
                   this.managedRequestsPagination.pages = this.getPages(this.managedRequestsPagination);
               },
               error =>  alert(error)
        );
    }

    getPages(pagination : any) {
        if (pagination.totalRecords == 0) {
          return [];
        } else {
          let records = pagination.totalRecords;
          let pages = [];
          for (let i = 0; records > 0; i++) {
            pages[i] = i + 1;
            records = records - pagination.rows;
          }
          return pages;
        }
      }

}