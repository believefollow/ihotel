import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DCkDetailComponent } from './d-ck-detail.component';

describe('Component Tests', () => {
  describe('DCk Management Detail Component', () => {
    let comp: DCkDetailComponent;
    let fixture: ComponentFixture<DCkDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DCkDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dCk: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DCkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DCkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dCk on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dCk).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
