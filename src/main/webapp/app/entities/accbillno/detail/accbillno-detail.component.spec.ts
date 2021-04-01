import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccbillnoDetailComponent } from './accbillno-detail.component';

describe('Component Tests', () => {
  describe('Accbillno Management Detail Component', () => {
    let comp: AccbillnoDetailComponent;
    let fixture: ComponentFixture<AccbillnoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccbillnoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accbillno: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccbillnoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccbillnoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accbillno on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accbillno).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
