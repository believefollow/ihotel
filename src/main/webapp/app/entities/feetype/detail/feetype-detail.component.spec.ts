import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FeetypeDetailComponent } from './feetype-detail.component';

describe('Component Tests', () => {
  describe('Feetype Management Detail Component', () => {
    let comp: FeetypeDetailComponent;
    let fixture: ComponentFixture<FeetypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FeetypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ feetype: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FeetypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeetypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feetype on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feetype).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
