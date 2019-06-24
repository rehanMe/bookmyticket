/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BookingappTestModule } from '../../../test.module';
import { TransportComponent } from 'app/entities/transport/transport.component';
import { TransportService } from 'app/entities/transport/transport.service';
import { Transport } from 'app/shared/model/transport.model';

describe('Component Tests', () => {
  describe('Transport Management Component', () => {
    let comp: TransportComponent;
    let fixture: ComponentFixture<TransportComponent>;
    let service: TransportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [TransportComponent],
        providers: []
      })
        .overrideTemplate(TransportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Transport(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
