import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import { Article } from '@app/shared/model/article.model';
import { ArticlesService } from '@app/shared/service/articles.service';
import { TagsService } from '@app/shared/service/tags.service';
import { Observable } from 'rxjs';

export const urlValidator: ValidatorFn =
   (control: AbstractControl): ValidationErrors | null => {
    let validUrl = true;

    try {
      new URL(control.value)
      // encodeURI(control.value);
    } catch {
      validUrl = false;
    }

    return validUrl ? null : { invalidUrl: true };
};

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {

  public articleForm: FormGroup;

  // public existedTags: string;
  public existedTags: (text: string) => Observable<any[]>;

  constructor(private fb: FormBuilder,
    public articleService: ArticlesService,
    public tagsService: TagsService) {
  }

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      url: new FormControl('', [
        Validators.required,
        Validators.minLength(11),
        Validators.maxLength(250),
        urlValidator
      ]),
      title: '',
      preambule: '',
      tags: []
    });

    this.tagsService.searchTags();
    this.existedTags = (text: string): Observable<any[]> => {
      return this.tagsService.tags$.asObservable();
    };

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

  // Getter for easy access
  get url() { return this.articleForm.get('url') };

}
