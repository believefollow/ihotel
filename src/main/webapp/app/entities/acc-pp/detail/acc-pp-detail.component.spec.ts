import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccPpDetailComponent } from './acc-pp-detail.component';

describe('Component Tests', () => {
  describe('AccPp Management Detail Component', () => {
    let comp: AccPpDetailComponent;
    let fixture: ComponentFixture<AccPpDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AccPpDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ accPp: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AccPpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccPpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load accPp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accPp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
