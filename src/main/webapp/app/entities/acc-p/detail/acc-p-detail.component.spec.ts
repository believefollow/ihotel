import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccPDetailComponent } from './acc-p-detail.component';

describe('Component Tests', () => {
  describe('AccP Management Detail Component', () => {
    let comp: AccPDetailComponent;
    let fixture: ComponentFixture<AccPDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccPDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accP: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accP on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accP).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
