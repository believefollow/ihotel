import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DKcDetailComponent } from './d-kc-detail.component';

describe('Component Tests', () => {
  describe('DKc Management Detail Component', () => {
    let comp: DKcDetailComponent;
    let fixture: ComponentFixture<DKcDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DKcDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dKc: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DKcDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DKcDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dKc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dKc).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
