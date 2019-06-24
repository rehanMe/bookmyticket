/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BookingappTestModule } from '../../../test.module';
import { AirplaneDetailComponent } from 'app/entities/airplane/airplane-detail.component';
import { Airplane } from 'app/shared/model/airplane.model';

describe('Component Tests', () => {
  describe('Airplane Management Detail Component', () => {
    let comp: AirplaneDetailComponent;
    let fixture: ComponentFixture<AirplaneDetailComponent>;
    const route = ({ data: of({ airplane: new Airplane(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BookingappTestModule],
        declarations: [AirplaneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AirplaneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AirplaneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.airplane).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
