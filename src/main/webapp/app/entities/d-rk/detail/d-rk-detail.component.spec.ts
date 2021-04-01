import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DRkDetailComponent } from './d-rk-detail.component';

describe('Component Tests', () => {
  describe('DRk Management Detail Component', () => {
    let comp: DRkDetailComponent;
    let fixture: ComponentFixture<DRkDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DRkDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dRk: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DRkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DRkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dRk on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dRk).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
