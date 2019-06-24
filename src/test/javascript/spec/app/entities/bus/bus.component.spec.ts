/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { BusComponent } from 'app/entities/bus/bus.component';
import { BusService } from 'app/entities/bus/bus.service';
import { Bus } from 'app/shared/model/bus.model';

describe('Component Tests', () => {
  describe('Bus Management Component', () => {
    let comp: BusComponent;
    let fixture: ComponentFixture<BusComponent>;
    let service: BusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [BusComponent],
        providers: []
      })
        .overrideTemplate(BusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bus(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.buses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
