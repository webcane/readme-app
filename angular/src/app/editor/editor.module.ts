import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditorComponent} from './editor.component';
import {EditorAuthResolver} from '@app/editor/editor-auth-resolver.service';
import {ReactiveFormsModule} from '@angular/forms';
import { TagInputModule } from 'ngx-chips';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 



@NgModule({
  declarations: [EditorComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    TagInputModule,
    BrowserAnimationsModule
  ],
  providers: [
    EditorAuthResolver
  ]
})
export class EditorModule {
}
