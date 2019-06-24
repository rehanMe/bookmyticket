/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { AirplaneComponent } from 'app/entities/airplane/airplane.component';
import { AirplaneService } from 'app/entities/airplane/airplane.service';
import { Airplane } from 'app/shared/model/airplane.model';

describe('Component Tests', () => {
  describe('Airplane Management Component', () => {
    let comp: AirplaneComponent;
    let fixture: ComponentFixture<AirplaneComponent>;
    let service: AirplaneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [AirplaneComponent],
        providers: []
      })
        .overrideTemplate(AirplaneComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AirplaneComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AirplaneService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Airplane(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.airplanes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
