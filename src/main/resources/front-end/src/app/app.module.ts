// BASE ANGULAR
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule, Http } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

// COMPONENTES
import { HeaderComponent }  from './header/header.component';
import { ContentComponent }  from './content/content.component';
import { FooterComponent }  from './footer/footer.component';
import { MenuComponent }  from './menu/menu.component';
import { ErrorModalComponent } from './modal/error-modal.component';
import { MyAccountComponent } from './myAccount/myAccount.component';
import { HomeComponent } from './home/home.component';
import { NewPetComponent } from './newPet/newPet.component';
import { MyPetsComponent } from './myPets/myPets.component';
import { EditPetComponent } from './editPet/editPet.component';
import { PetDetailComponent } from './petDetail/petDetail.component';
import { RequestsComponent } from './requests/requests.component';

// SERVICIOS
import { UserService } from './services/user.service';

// MODULOS
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader} from '@ngx-translate/http-loader';
import { SelectModule } from 'ng-select';

import { environment } from '../environments/environment';

export function HttpLoaderFactory(http: Http) {
    return new TranslateHttpLoader(http, environment.i18nPath + "/assets/i18n/", ".json");
}

// RUTAS
const appRoutes: Routes = [
  { path: 'myAccount', component: MyAccountComponent },
  { path: 'association/newPet', component: NewPetComponent },
  { path: 'association/myPets', component: MyPetsComponent },
  { path: 'association/editPet/:petId', component: EditPetComponent },
  { path: 'pet/:petId', component: PetDetailComponent },
  { path: 'user/requests', component: RequestsComponent},
  { path: '**', component: HomeComponent }
];

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
    }),
    RouterModule.forRoot(appRoutes),
    SelectModule,
    
  ],
  declarations: [
  	HeaderComponent, 
    ContentComponent,
  	FooterComponent, 
  	MenuComponent,
    ErrorModalComponent,
    MyAccountComponent,
    HomeComponent,
    NewPetComponent,
    MyPetsComponent,
    EditPetComponent,
    PetDetailComponent,
    RequestsComponent 
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
