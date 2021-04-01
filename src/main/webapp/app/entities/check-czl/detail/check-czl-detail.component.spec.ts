import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CheckCzlDetailComponent } from './check-czl-detail.component';

describe('Component Tests', () => {
  describe('CheckCzl Management Detail Component', () => {
    let comp: CheckCzlDetailComponent;
    let fixture: ComponentFixture<CheckCzlDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CheckCzlDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ checkCzl: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CheckCzlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckCzlDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load checkCzl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.checkCzl).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
