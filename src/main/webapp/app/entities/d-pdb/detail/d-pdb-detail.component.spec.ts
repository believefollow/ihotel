import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DPdbDetailComponent } from './d-pdb-detail.component';

describe('Component Tests', () => {
  describe('DPdb Management Detail Component', () => {
    let comp: DPdbDetailComponent;
    let fixture: ComponentFixture<DPdbDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DPdbDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dPdb: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DPdbDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DPdbDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dPdb on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dPdb).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
