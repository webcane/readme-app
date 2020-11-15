import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditorComponent} from './editor.component';
import {EditorAuthResolver} from '@app/editor/editor-auth-resolver.service';


@NgModule({
  declarations: [EditorComponent],
  imports: [
    CommonModule
  ],
  providers: [
    EditorAuthResolver
  ]
})
export class EditorModule {
}
