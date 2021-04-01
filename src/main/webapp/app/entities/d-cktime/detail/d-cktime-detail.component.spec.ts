import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DCktimeDetailComponent } from './d-cktime-detail.component';

describe('Component Tests', () => {
  describe('DCktime Management Detail Component', () => {
    let comp: DCktimeDetailComponent;
    let fixture: ComponentFixture<DCktimeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DCktimeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dCktime: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DCktimeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DCktimeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dCktime on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dCktime).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
