// BASE ANGULAR
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

// COMPONENTES
import { HeaderComponent }  from './header/header.component';
import { ContentComponent }  from './content/content.component';
import { FooterComponent }  from './footer/footer.component';
import { MenuComponent }  from './menu/menu.component';

// MODULOS
import { Ng2Bs3ModalModule } from 'ng2-bs3-modal/ng2-bs3-modal';

@NgModule({
  imports: [
  	BrowserModule,
  	Ng2Bs3ModalModule,
  	FormsModule
  ],
  declarations: [
  	HeaderComponent, 
  	ContentComponent, 
  	FooterComponent, 
  	MenuComponent
  ],
  bootstrap: [
  	HeaderComponent, 
  	ContentComponent, 
  	FooterComponent, 
  	MenuComponent
  ],
  // componentes con modales
  entryComponents: [

  ]
})
export class AppModule { }
