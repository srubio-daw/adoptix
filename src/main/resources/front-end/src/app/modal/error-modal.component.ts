import { Component, ViewChild } from '@angular/core';

// MODULES
import { TranslateService } from '@ngx-translate/core';
import { ModalComponent } from 'ng2-bs3-modal/ng2-bs3-modal';

@Component({
	selector: 'error-modal',
	template: `
		<modal #errorModal>
		    <modal-header [show-close]="true">
		        <h4 class="modal-title">{{titleKey| translate}}</h4>
		    </modal-header>
		    <modal-body>
				<p>{{messageKey | translate}}</p>
		    </modal-body>
		    <modal-footer [show-default-buttons]="false">
		        <button type="button" class="btn btn-primary" (click)="errorModal.close()">
		            <span translate="button.close"></span>
		        </button>
		    </modal-footer>
		</modal>
	`

})
export class ErrorModalComponent {
	// Constructor
	constructor(translate: TranslateService) {
        // this language will be used as a fallback when a translation isn't found in the current language
        translate.setDefaultLang('es');
         // the lang to use, if the lang isn't available, it will use the current loader to get them
        translate.use('es');
    }

    // Variables
    titleKey : String;
    messageKey : String

    // Modal
    @ViewChild('errorModal')
    errorModal : ModalComponent;

    public open(titleKey : String, messageKey : String) {
    	this.titleKey = titleKey;
    	this.messageKey = messageKey;
    	this.errorModal.open();
    }

    public close() {
    	this.errorModal.close();
    } 
}