import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DDepotDetailComponent } from './d-depot-detail.component';

describe('Component Tests', () => {
  describe('DDepot Management Detail Component', () => {
    let comp: DDepotDetailComponent;
    let fixture: ComponentFixture<DDepotDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DDepotDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dDepot: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DDepotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DDepotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dDepot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dDepot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
