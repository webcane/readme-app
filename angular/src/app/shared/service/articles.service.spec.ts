/* eslint-disable no-unused-vars,@typescript-eslint/no-unused-vars */

import { TestBed, inject } from '@angular/core/testing';
import { ArticlesService } from './articles.service';

describe('Service: Articles', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArticlesService]
    });
  });

  it('should ...', inject([ArticlesService], (service: ArticlesService) => {
    expect(service).toBeTruthy();
  }));
});
