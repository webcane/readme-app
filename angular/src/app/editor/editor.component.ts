import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  public articleForm: FormGroup;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      url: '',
      title: '',
      preambule: ''
    });

    // this.articleForm.patchValue(res);
  }

  publish(): void {
    console.log("publish article");

    // this.myDataService.submitUpdate(this.articleForm.getRawValue())
    //   .subscribe((): void => {
    //     alert('Saved!');
    //   });
  }
}
