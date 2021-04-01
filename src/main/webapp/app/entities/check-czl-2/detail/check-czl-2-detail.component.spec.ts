import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckCzl2DetailComponent } from './check-czl-2-detail.component';

describe('Component Tests', () => {
  describe('CheckCzl2 Management Detail Component', () => {
    let comp: CheckCzl2DetailComponent;
    let fixture: ComponentFixture<CheckCzl2DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckCzl2DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkCzl2: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckCzl2DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckCzl2DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkCzl2 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkCzl2).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
