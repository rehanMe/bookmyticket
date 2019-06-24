/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { DestinationComponent } from 'app/entities/destination/destination.component';
import { DestinationService } from 'app/entities/destination/destination.service';
import { Destination } from 'app/shared/model/destination.model';

describe('Component Tests', () => {
  describe('Destination Management Component', () => {
    let comp: DestinationComponent;
    let fixture: ComponentFixture<DestinationComponent>;
    let service: DestinationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [DestinationComponent],
        providers: []
      })
        .overrideTemplate(DestinationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DestinationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DestinationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Destination(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.destinations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
