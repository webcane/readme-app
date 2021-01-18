import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditorComponent} from './editor.component';
import {EditorAuthResolver} from '@app/editor/editor-auth-resolver.service';
import {ReactiveFormsModule} from '@angular/forms';


@NgModule({
  declarations: [EditorComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  providers: [
    EditorAuthResolver
  ]
})
export class EditorModule {
}
