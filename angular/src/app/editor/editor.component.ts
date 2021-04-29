import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import { Article } from '@app/shared/model/article.model';
import { ArticlesService } from '@app/shared/service/articles.service';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  public articleForm: FormGroup;

  constructor(private fb: FormBuilder,
    public articleService: ArticlesService) {
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
    console.log("publish article " + JSON.stringify(this.articleForm.getRawValue()));

    this.articleService
      .create(this.articleForm.getRawValue() as Article)
      .subscribe((): void => {
        this.articleForm.reset();
      }, error => {
        console.log("TODO show popup");
      });
  }
}
