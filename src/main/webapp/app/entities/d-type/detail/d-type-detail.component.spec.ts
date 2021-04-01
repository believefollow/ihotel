import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DTypeDetailComponent } from './d-type-detail.component';

describe('Component Tests', () => {
  describe('DType Management Detail Component', () => {
    let comp: DTypeDetailComponent;
    let fixture: ComponentFixture<DTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dType: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
