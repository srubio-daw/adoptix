// BASE ANGULAR
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';

// COMPONENTES
import { HeaderComponent }  from './header/header.component';
import { ContentComponent }  from './content/content.component';
import { FooterComponent }  from './footer/footer.component';
import { MenuComponent }  from './menu/menu.component';
import { ErrorModalComponent } from './modal/error-modal.component';

// SERVICIOS
import { UserService } from './services/user.service';

// MODULOS
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader} from '@ngx-translate/http-loader';

import { environment } from '../environments/environment';

export function HttpLoaderFactory(http: Http) {
    return new TranslateHttpLoader(http, environment.i18nPath + "/assets/i18n/", ".json");
}

@NgModule({
  imports: [
  	BrowserModule,
  	Ng2Bs3ModalModule,
  	FormsModule,
    ReactiveFormsModule,
    HttpModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [Http]
      }
    })
  ],
  declarations: [
  	HeaderComponent, 
  	ContentComponent, 
  	FooterComponent, 
  	MenuComponent,
    ErrorModalComponent

  ],
  bootstrap: [
  	HeaderComponent, 
  	ContentComponent, 
  	FooterComponent, 
  	MenuComponent
  ],
  providers: [
    UserService
  ]
})
export class AppModule { 

}
