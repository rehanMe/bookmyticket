/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { TrainComponent } from 'app/entities/train/train.component';
import { TrainService } from 'app/entities/train/train.service';
import { Train } from 'app/shared/model/train.model';

describe('Component Tests', () => {
  describe('Train Management Component', () => {
    let comp: TrainComponent;
    let fixture: ComponentFixture<TrainComponent>;
    let service: TrainService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [TrainComponent],
        providers: []
      })
        .overrideTemplate(TrainComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TrainComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TrainService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Train(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.trains[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
