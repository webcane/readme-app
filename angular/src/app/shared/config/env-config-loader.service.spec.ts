/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { EnvConfigLoaderService } from './env-config-loading.service';

describe('Service: Loading', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EnvConfigLoaderService]
    });
  });

  it('should ...', inject([EnvConfigLoaderService], (service: EnvConfigLoaderService) => {
    expect(service).toBeTruthy();
  }));
});
