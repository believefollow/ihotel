import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DDbDetailComponent } from './d-db-detail.component';

describe('Component Tests', () => {
  describe('DDb Management Detail Component', () => {
    let comp: DDbDetailComponent;
    let fixture: ComponentFixture<DDbDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DDbDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dDb: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DDbDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DDbDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dDb on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dDb).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
