import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DDeptDetailComponent } from './d-dept-detail.component';

describe('Component Tests', () => {
  describe('DDept Management Detail Component', () => {
    let comp: DDeptDetailComponent;
    let fixture: ComponentFixture<DDeptDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DDeptDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dDept: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DDeptDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DDeptDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dDept on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dDept).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
