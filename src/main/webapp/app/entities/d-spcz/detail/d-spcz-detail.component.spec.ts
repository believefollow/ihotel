import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DSpczDetailComponent } from './d-spcz-detail.component';

describe('Component Tests', () => {
  describe('DSpcz Management Detail Component', () => {
    let comp: DSpczDetailComponent;
    let fixture: ComponentFixture<DSpczDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DSpczDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dSpcz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DSpczDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DSpczDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dSpcz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dSpcz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
