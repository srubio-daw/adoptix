import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HeaderComponent }  from './header/header.component';
import { ContentComponent }  from './content/content.component';
import { FooterComponent }  from './footer/footer.component';
import { MenuComponent }  from './menu/menu.component';

@NgModule({
  imports:      [ BrowserModule ],
  declarations: [ HeaderComponent, ContentComponent, FooterComponent, MenuComponent ],
  bootstrap:    [ HeaderComponent, ContentComponent, FooterComponent, MenuComponent ]
})
export class AppModule { }
